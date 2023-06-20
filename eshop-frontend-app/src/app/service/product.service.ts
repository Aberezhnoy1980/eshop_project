import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../model/page";
import {ProductFilters} from "../model/ProductFilters";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {
  }

  public findAll(productFilters?: ProductFilters, page?: number): Observable<Page> {
    let params = new HttpParams();
    if (productFilters) {
      params = params.set('namePattern', productFilters.name);
      params = params.set('minPrice', productFilters.minPrice);
      params = params.set('maxPrice', productFilters.maxPrice);
      params = params.set('categoryName', productFilters.categoryName);
      params = params.set('brandName', productFilters.brandName);
      params = params.set('brandId', productFilters.brandId);
      params = params.set('categoryId', productFilters.categoryId);
    }
    params = params.set("page", page != null ? page : 1);
    params = params.set("size", 3);
    return this.http.get<Page>('api/v1/product/all', {params});
  }

  findProductById(id: bigint): Observable<Product> {
    return this.http.get<Product>('api/v1/product/' + id);
  }
}
