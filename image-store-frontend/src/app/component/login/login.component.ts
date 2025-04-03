import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { LoginService } from '../../service/login/login.service';
import { LoginDto } from '../../service/login/login-dto';
import { Router, RouterLink } from '@angular/router';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-login',
  imports: [FormsModule, NgIf, RouterLink],
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  email: string = '';
  password: string = '';
  errorMessage: string = '';

  submitForm(form: any): void {

    console.log("Form: " + form.valid);

    if (form.valid) {

      let login: LoginDto = { email: this.email, password: this.password }

      this.loginService.login(login).subscribe({

        next: (loginData) => {

          sessionStorage.setItem(environment.tokenId, loginData.token);
          this.router.navigate(['/list-images']);

        },

        error: (error) => this.errorMessage = error

      });

    }

  }

  ngOnInit(): void {

    let isLogged: boolean = (sessionStorage.getItem(environment.tokenId) != null);

    if (isLogged) {
      this.router.navigate(['/list-images']);
    }

  }

  constructor(private loginService: LoginService, private router: Router) { }

}
