import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  logOut() {
    sessionStorage.removeItem(environment.tokenId);
    this.router.navigate(['/login'])
  }

  constructor(private router: Router) { }
}
