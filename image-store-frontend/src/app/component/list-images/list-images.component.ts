import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Router, RouterLink } from '@angular/router';
import { ListImagesService } from '../../service/list-images/list-images.service';
import { ImageDto } from '../../service/list-images/image-dto';

@Component({
  selector: 'app-list-images',
  imports: [RouterLink],
  templateUrl: './list-images.component.html',
  styleUrl: './list-images.component.css'
})
export class ListImagesComponent implements OnInit {

  url: string = `${environment.apiHost}/download/`;

  imageList: ImageDto[] = [];

  logout(): void {
    sessionStorage.removeItem(environment.tokenId);
    this.router.navigate(['']);
  }

  listImages(): void {
    this.listImagesService.listImages().subscribe({
      next: (imageList) => {
        this.imageList = imageList;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  ngOnInit(): void {

    let isLogged: boolean = (sessionStorage.getItem(environment.tokenId) != null);

    if (!isLogged) {
      this.router.navigate(['/login']);
    }

    this.listImages();

  }

  constructor(private router: Router, private listImagesService: ListImagesService) { }

}
