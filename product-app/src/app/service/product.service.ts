import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ProductInterface} from "../interface/product.interface";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  productUrl = 'http://localhost:8080/producto/';

  constructor(private httpClient: HttpClient) {
  }

  public list(): Observable<ProductInterface[]> {
    return this.httpClient.get<ProductInterface[]>(this.productUrl + 'lista');
  }

  public detail(id: number): Observable<ProductInterface> {
    return this.httpClient.get<ProductInterface>(this.productUrl + `detail/${id}`);
  }

  public detailName(nombre: string): Observable<ProductInterface> {
    return this.httpClient.get<ProductInterface>(this.productUrl + `detailname/${nombre}`);
  }

  public save(producto: ProductInterface): Observable<any> {
    return this.httpClient.post<any>(this.productUrl + 'create', producto);
  }

  public update(id: number, producto: ProductInterface): Observable<any> {
    return this.httpClient.put<any>(this.productUrl + `update/${id}`, producto);
  }

  public delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(this.productUrl + `delete/${id}`);
  }
}
