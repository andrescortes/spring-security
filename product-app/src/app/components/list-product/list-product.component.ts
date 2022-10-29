import {Component, OnInit} from '@angular/core';
import {ProductInterface} from "../../interface/product.interface";
import {ProductService} from "../../service/product.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-list-product',
  templateUrl: './list-product.component.html',
  styleUrls: ['./list-product.component.css']
})
export class ListProductComponent implements OnInit {

  products: ProductInterface[] = [];

  constructor(
    private productService: ProductService,
    private toastr: ToastrService
  ) {
  }

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.list().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  delete(id: number) {
    this.productService.delete(id).subscribe({
        next: (data) => {
          this.toastr.success('Product deleted', 'OK', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
          this.loadProducts();
        },
        error: (err) => {
          this.toastr.error(err.error.mensaje, 'Fail', {
            timeOut: 3000, positionClass: 'toast-top-center',
          });
        }
      }
    );
  }

}
