import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ImagePipe } from '../../pipe/image.pipe';
import { AsyncPipe, NgIf } from '@angular/common';
import { ImageService } from '../../service/image/image.service';
import { ResizeImageDto } from '../../service/image/resize-image-dto';

@Component({
  selector: 'app-image',
  imports: [ImagePipe, AsyncPipe, RouterLink, NgIf],
  templateUrl: './image.component.html',
  styleUrl: './image.component.css'
})
export class ImageComponent implements OnInit {

  url: string = environment.apiHost + '/image-store/download';

  fileUrl: string = ''

  fileName: string = '';

  pane: string = 'MAIN';

  resize() {

    let resizeImageDto: ResizeImageDto = { fileName: this.fileName, percentage: 50 }

    this.imageService.resize(resizeImageDto).subscribe({

      next: () => { this.pane = 'RESIZE_MESSAGE'; },

      error: (error) => console.log(error)

    })

  }

  ngOnInit(): void {

    this.route.paramMap.subscribe((params) => {

      this.fileUrl = `${this.url}/${params.get("fileName")}`;
      this.fileName = params.get("fileName") + '';

    });

  }

  constructor(private route: ActivatedRoute, private imageService: ImageService) { }

}
