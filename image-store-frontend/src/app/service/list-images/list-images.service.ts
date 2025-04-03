import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { ImageDto } from './image-dto';
import { Observable, retry, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ListImagesService {

  url: string = `${environment.apiHost}/image-store`;

  httpOptions = {

    headers: new HttpHeaders({

      'Authorization': `Bearer ${sessionStorage.getItem(environment.tokenId)}`

    })

  }

  listImages(): Observable<ImageDto[]> {

    let url = this.url;

    return this.httpClient
      .get<ImageDto[]>(url, this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      )

  }

  handleError(error: HttpErrorResponse) {

    let errorMessage = '';

    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = error.message;
    }

    return throwError(() => new Error(errorMessage));

  };

  constructor(private httpClient: HttpClient) { }
}
