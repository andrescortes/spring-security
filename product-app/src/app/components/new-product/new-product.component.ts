import {Component, OnInit} from '@angular/core';
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";
import {ProductService} from "../../service/product.service";
import {ProductInterface} from "../../interface/product.interface";

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent implements OnInit {

  name:string = '';
  price: number | undefined = null;

  constructor(
    private productService: ProductService,
    private toastr: ToastrService,
    private router: Router

  ) {
  }

  ngOnInit() {
  }

  onCreate(): void {
    let product: ProductInterface = {
      name: this.name,
      price: this.price
    }
    this.productService.save(product).subscribe({
        next: (data) => {
          this.toastr.success('Product created', 'OK', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.toastr.error(err.error.mensaje, 'Fail', {
            timeOut: 3000, positionClass: 'toast-top-center',
          });
          this.router.navigate(['/']);
        }
      }
    );
  }

}
