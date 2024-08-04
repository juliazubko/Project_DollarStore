import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginMainComponent } from './login-main/login-main.component';
import { LoginChooseComponent } from './login-choose/login-choose.component';
import { MittkontoComponent } from './mittkonto/mittkonto.component';
import { MinakvittonComponent } from './minakvitton/minakvitton.component';
import { RegistreraComponent } from './registrera/registrera.component';
import { AccountpageComponent } from './accountpage/accountpage.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path: '', component: LoginMainComponent },
  { path: 'loginchoose', component: LoginChooseComponent },
  { path: 'registrera', component: RegistreraComponent },
  { path: 'account', component: AccountpageComponent, canActivate: [AuthGuard], children: [
    { path: 'mittkonto', component: MittkontoComponent, canActivate: [AuthGuard] },
    { path: 'minakvitton', component: MinakvittonComponent, canActivate: [AuthGuard] },
    { path: '', redirectTo: 'mittkonto', pathMatch: 'full' },
  ]},
  { path: 'forgot', component: ForgotPasswordComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
