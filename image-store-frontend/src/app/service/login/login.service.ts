import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginDto } from './login-dto';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { LoginDataDto } from '../../dto/login-data-dto';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url: string = `${environment.apiHost}/image-user`;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-type': 'application/json' })
  }

  login(login: LoginDto): Observable<LoginDataDto> {

    let url = this.url + '/login';

    return this.httpClient.post<LoginDataDto>(url, JSON.stringify(login), this.httpOptions)
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
      if (error.status == 403) {
        errorMessage = 'Wrong user or password.';
      } else {
        errorMessage = error.message;
      }
    }

    return throwError(() => new Error(errorMessage));

  };


  constructor(private httpClient: HttpClient) { }

}
