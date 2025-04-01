import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { environment } from '../../../environments/environment';
import { SecurityService } from '../../service/security.service';

@Component({
  selector: 'app-upload',
  imports: [RouterLink],
  templateUrl: './upload.component.html',
  styleUrl: './upload.component.css'
})
export class UploadComponent {

  url: string = environment.apiHost + '/image-store/upload';
  httpOptions = {

    headers: new HttpHeaders({

      'Authorization': `Bearer ${sessionStorage.getItem(environment.tokenId)}`

    })

  }

  fileName = '';

  constructor(private http: HttpClient, private security: SecurityService, private router: Router) { }

  onFileSelected(event: { target: { files: File[]; }; }) {

    const file: File = event.target.files[0];

    if (file) {

      this.fileName = file.name;

      const formData = new FormData();

      formData.append("file", file);

      const upload$ = this.http.post(this.url, formData, this.httpOptions);

      upload$.subscribe({
        next: () => this.router.navigate(['/list-images']),
        error: () => this.router.navigate(['/list-images'])
      });
    }
  }

}
