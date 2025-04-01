import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Pipe, PipeTransform } from '@angular/core';
import { ImageService } from '../service/image.service';
import { SecurityService } from '../service/security.service';
import { Observable } from 'rxjs';
import { lastValueFrom } from 'rxjs';

@Pipe({
  name: 'image'
})
export class ImagePipe implements PipeTransform {

  constructor(
    private http: HttpClient,
    private security: SecurityService
  ) { }

  async transform(src: string): Promise<string> {

    const token = this.security.getToken();

    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    const imageBlob = this.http.get(src, { headers, responseType: 'blob' });

    const imageBlob1 = await lastValueFrom(imageBlob);
    
    const reader = new FileReader();

    return new Promise((resolve, reject) => {

      reader.onloadend = () => resolve(reader.result as string);

      reader.readAsDataURL(imageBlob1);

    });

  }

}
function firstValueFrom(arg0: Observable<ArrayBuffer>) {
  throw new Error('Function not implemented.');
}

