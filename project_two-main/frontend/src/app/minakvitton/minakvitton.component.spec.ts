import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MinakvittonComponent } from './minakvitton.component';

describe('MinakvittonComponent', () => {
  let component: MinakvittonComponent;
  let fixture: ComponentFixture<MinakvittonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MinakvittonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MinakvittonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
