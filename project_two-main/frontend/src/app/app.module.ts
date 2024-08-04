import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; // forms
import { NgxPaginationModule } from 'ngx-pagination';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { MittkontoComponent } from './mittkonto/mittkonto.component';
import { MinakvittonComponent } from './minakvitton/minakvitton.component';
import { DollarHeaderComponent } from './dollar-header/dollar-header.component';
import { LoginMainComponent } from './login-main/login-main.component';
import { LoginChooseComponent } from './login-choose/login-choose.component';
import { RegistreraComponent } from './registrera/registrera.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { AccountpageComponent } from './accountpage/accountpage.component';
import { ConfirmDeletionDialogComponent } from './confirm-deletion-dialog/confirm-deletion-dialog.component';
import { UserNotFoundComponent } from './user-not-found/user-not-found.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { AuthService } from './auth.service';
import { AuthGuard } from './auth.guard';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    MittkontoComponent,
    MinakvittonComponent,
    DollarHeaderComponent,
    LoginMainComponent,
    LoginChooseComponent,
    RegistreraComponent,
    SidebarComponent,
    AccountpageComponent,
    ConfirmDeletionDialogComponent,
    UserNotFoundComponent,
    ForgotPasswordComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    NgxPaginationModule
  ],
  providers: [
    AuthService, AuthGuard,
    provideClientHydration(),
    provideHttpClient(withFetch())
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
