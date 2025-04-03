import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { ResizeImageDto } from './resize-image-dto';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { SecurityService } from '../security/security.service';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  url: string = `${environment.apiHost}/image-handler`;

  resize(resizeParams: ResizeImageDto): Observable<any> {

    let url = this.url;

    let httpOptions = {
      headers: new HttpHeaders({
        'Content-type': 'application/json',
        'Authorization': `Bearer ${this.securityService.getToken()}`
      })
    }

    return this.httpClient.post<any>(url, JSON.stringify(resizeParams), httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  handleError(error: HttpErrorResponse) {

    let errorMessage = '';

    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      if (error.status == 403) {
        errorMessage = 'Wrong user or password.';
      } else {
        errorMessage = error.message;
      }
    }

    return throwError(() => new Error(errorMessage));

  }


  constructor(private httpClient: HttpClient, private securityService: SecurityService) { }

}
