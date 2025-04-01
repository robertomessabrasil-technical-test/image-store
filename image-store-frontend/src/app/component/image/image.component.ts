import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ImagePipe } from '../../pipe/image.pipe';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-image',
  imports: [ImagePipe, AsyncPipe, RouterLink],
  templateUrl: './image.component.html',
  styleUrl: './image.component.css'
})
export class ImageComponent implements OnInit {

  url: string = environment.apiHost + '/download';
  fileName: string = '';

  ngOnInit(): void {

    this.route.paramMap.subscribe((params) => {

      this.fileName = environment.apiHost + '/image-store/download/' + params.get("fileName");

    });

  }

  constructor(private route: ActivatedRoute) { }

}
