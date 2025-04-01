import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ImagePipe } from '../../pipe/image.pipe';
import { AsyncPipe, NgIf } from '@angular/common';

@Component({
  selector: 'app-image',
  imports: [ImagePipe, AsyncPipe, RouterLink, NgIf],
  templateUrl: './image.component.html',
  styleUrl: './image.component.css'
})
export class ImageComponent implements OnInit {

  url: string = environment.apiHost + '/image-store/download';
  fileName: string = '';

  pane: string = 'MAIN';

  resize() {
    this.pane = 'RESIZE_MESSAGE';
  }

  ngOnInit(): void {

    this.route.paramMap.subscribe((params) => {

      this.fileName = `${this.url}/${params.get("fileName")}`;

    });

  }

  constructor(private route: ActivatedRoute) { }

}
