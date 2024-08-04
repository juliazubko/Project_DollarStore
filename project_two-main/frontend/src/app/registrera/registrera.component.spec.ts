import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistreraComponent } from './registrera.component';

describe('RegistreraComponent', () => {
  let component: RegistreraComponent;
  let fixture: ComponentFixture<RegistreraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegistreraComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistreraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
