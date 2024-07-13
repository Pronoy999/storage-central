import { TestBed } from '@angular/core/testing';

import { StorageCentralAPIService } from './storage-central-api.service';

describe('StorageCentralAPIService', () => {
    let service: StorageCentralAPIService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(StorageCentralAPIService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
