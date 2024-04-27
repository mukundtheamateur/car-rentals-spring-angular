import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class CookieServices {

  constructor(private cookie:CookieService) { }

  setCookie(key:string,value:any,option?:any){
    if(option === undefined){
      const expire = new Date();
      expire.setHours(expire.getHours()+1);
      option = {
        expires:expire,
        sameSite:'strict'
      }
    }
    this.cookie.set(key,value,option);
  }

  getCookie(key:string){
    return this.cookie.get(key);
  }

  checkCookie(key:string):boolean{
    return this.cookie.check(key);
  }

  deleteCookie(key:string){
    this.cookie.delete(key);
  }
}
