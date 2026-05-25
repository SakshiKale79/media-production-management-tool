import { bootstrapApplication } from '@angular/platform-browser';

import {provideHttpClient, withInterceptors} from '@angular/common/http'
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';

bootstrapApplication(AppComponent, {
  providers:[
    provideHttpClient(withInterceptors([
      (req, next)=>{
        req= req.clone({ withCredentials: true});
          return next(req);
      }
    ])),
    provideRouter(routes)
  ]
})
  
