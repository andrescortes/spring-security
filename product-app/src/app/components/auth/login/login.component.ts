import {Component, OnInit} from '@angular/core';
import {TokenService} from "../../../service/token.service";
import {AuthService} from "../../../service/auth.service";
import {Router} from "@angular/router";
import {UserLoginInterface} from "../../../interface/user-login-interface";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  isLogged: boolean = false;
  isLoggingFail: boolean = false;
  userLogin: UserLoginInterface = null;
  username: string = '';
  password: string = '';
  roles: string[] = [];
  errorMessage: string = '';


  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.roles = this.tokenService.getAuthorities();
    }
  }

  onLogin(): void {
    this.userLogin = {
      username: this.username,
      password: this.password
    }
    this.authService.login(this.userLogin).subscribe({
      next: (data) => {
        console.table(data);
        this.isLogged = true;
        this.tokenService.setToken(data.token);
        this.tokenService.setUsername(data.username);
        this.tokenService.setAuthorities(data.authorities);
        this.roles = data.authorities;
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error(err);
        this.isLogged = false;
        this.isLoggingFail = true;
        this.errorMessage = err.error.message;
        console.log(this.errorMessage);
      }
    });
  }

}
