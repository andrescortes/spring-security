import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NewUserInterface} from "../interface/new-user-interface";
import {Observable} from "rxjs";
import {UserLoginInterface} from "../interface/user-login-interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authURL = 'http://localhost:8080/auth';


  constructor( private httpClient: HttpClient) { }

  public addUser(newUser: NewUserInterface): Observable<any> {
    return this.httpClient.post(this.authURL + '/save', newUser);
  }

  public login(userLogin: UserLoginInterface): Observable<any> {
    return this.httpClient.post(this.authURL + '/login', userLogin);
  }
}
