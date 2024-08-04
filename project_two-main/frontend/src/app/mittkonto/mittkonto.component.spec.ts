import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MittkontoComponent } from './mittkonto.component';

describe('MittkontoComponent', () => {
  let component: MittkontoComponent;
  let fixture: ComponentFixture<MittkontoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MittkontoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MittkontoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
