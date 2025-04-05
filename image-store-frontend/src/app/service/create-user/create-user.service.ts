import { HttpHeaders, HttpErrorResponse, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, retry, catchError, throwError } from 'rxjs';
import { CreateUserDto } from './create-user-dto';
import { environment } from '../../../environments/environment';
import { UserDto } from './user-dto';

@Injectable({
  providedIn: 'root'
})
export class CreateUserService {

  url: string = `${environment.apiHost}/image-user`;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-type': 'application/json' })
  }

  createUser(createUser: CreateUserDto): Observable<UserDto> {

    let url = this.url;

    return this.httpClient.post<UserDto>(url, JSON.stringify(createUser), this.httpOptions)
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
        errorMessage = 'Invalid email or password';
      } else {
        errorMessage = error.message;
      }
    }

    return throwError(() => new Error(errorMessage));

  };


  constructor(private httpClient: HttpClient) { }

}
