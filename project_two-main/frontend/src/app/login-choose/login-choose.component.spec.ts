import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginChooseComponent } from './login-choose.component';

describe('LoginChooseComponent', () => {
  let component: LoginChooseComponent;
  let fixture: ComponentFixture<LoginChooseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginChooseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginChooseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
