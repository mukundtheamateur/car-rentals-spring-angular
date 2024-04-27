import { HttpInterceptorFn, HttpHeaders } from "@angular/common/http";

export const HeaderInterceptor: HttpInterceptorFn = (req,next)=>{
  const jwtToken = localStorage.getItem('jwt');
  if(jwtToken){
    const reqClone = req.clone({
      headers: new HttpHeaders({
        'Authorization': `Bearer ${JSON.parse(jwtToken)}`
      }),
      withCredentials: true
    });
    return next(reqClone);
  }
  return next(req);
}
