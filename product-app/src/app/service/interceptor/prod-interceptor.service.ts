import {Injectable} from '@angular/core';
import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {Observable} from "rxjs";
import {TokenService} from "../token.service";

@Injectable({
  providedIn: 'root'
})
export class ProdInterceptorService implements HttpInterceptor {

  constructor(private tokenService: TokenService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let interceptorReq: HttpRequest<any> = req;
    const token: string = this.tokenService.getToken();
    if (token) {
      interceptorReq = req.clone({headers: req.headers.set('Authorization', 'Bearer ' + token)})
    }
    return next.handle(interceptorReq);
  }
}

export const interceptorProvider = [{
  provide: HTTP_INTERCEPTORS,
  useClass: ProdInterceptorService,
  multi: true

}]


