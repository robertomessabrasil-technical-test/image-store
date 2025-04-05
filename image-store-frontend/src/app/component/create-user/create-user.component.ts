import { Component, OnInit } from '@angular/core';
import { CreateUserService } from '../../service/create-user/create-user.service';
import { CreateUserDto } from '../../service/create-user/create-user-dto';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-create-user',
  imports: [FormsModule, NgIf, RouterLink],
  templateUrl: './create-user.component.html',
  styleUrl: './create-user.component.css'
})
export class CreateUserComponent implements OnInit {

  panel: string = 'FORM';

  email: string = '';
  password: string = '';
  errorMessage: string = '';

  submitForm(form: any): void {

    console.log("Form: " + form.valid);

    if (form.valid) {

      let createUser: CreateUserDto = { email: this.email, password: this.password, role: "ROLE_CUSTOMER" }

      this.createUserService.createUser(createUser).subscribe({
        next: () => { this.panel = 'MSG' },
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

  constructor(private createUserService: CreateUserService, private router: Router) { }

}
