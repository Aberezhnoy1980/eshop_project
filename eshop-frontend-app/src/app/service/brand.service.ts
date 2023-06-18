import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Brand} from "../model/brand";

@Injectable({
  providedIn: 'root'
})
export class BrandService {

  constructor(private http: HttpClient) {
  }

  findALl() {
    return this.http.get<Brand[]>('api/v1/product/brands');
  }
}
