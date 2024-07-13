import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
    const router = inject(Router)

    const loggedInUser = window.sessionStorage.getItem('auth-user');

    if (loggedInUser != '' && loggedInUser != null)
        return true;
    else {
        router.navigateByUrl('');
        return false;
    }
};