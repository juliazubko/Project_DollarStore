import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DollarHeaderComponent } from './dollar-header.component';

describe('DollarHeaderComponent', () => {
  let component: DollarHeaderComponent;
  let fixture: ComponentFixture<DollarHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DollarHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DollarHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
