import { HttpResponse, HttpInterceptorFn } from '@angular/common/http';
import { tap } from 'rxjs/operators';

export const ResponseInterceptor:HttpInterceptorFn = (req,next)=>{
    return next(req).pipe(
      tap({
        next: event => {
          if (event instanceof HttpResponse) {
            console.log('Response received in interceptor:', event);
            // Store the headers and other details in local storage
            localStorage.setItem('responseHeaders', JSON.stringify(event.headers));
            localStorage.setItem('responseStatus', event.status.toString());
            localStorage.setItem('responseStatusText', event.statusText);
            // If there are other details you want to store, you can do so here
          }
        },
        error: error => {
          console.error('Error received in interceptor:', error);
        }
      })
    );

}


/* Intercepting the Response: After a request is sent to the server, a response is returned. This response is intercepted by the ResponseInterceptor.
Checking the Event Type: The tap operator checks if the event is an instance of HttpResponse using the line if (event instanceof HttpResponse). If it is, this means that a response has been received from the server.
Logging the Response: If the event is a HttpResponse, the interceptor logs the response to the console with console.log('Response received in interceptor:', event);. This allows you to see the full response, including headers, body, and status, in your browser’s console.
Continuing the Observable Stream: After logging, the HttpResponse continues through the Observable stream. If there are more interceptors, they will process this response next. Finally, the response is returned to the service or component that made the original HTTP request. */
