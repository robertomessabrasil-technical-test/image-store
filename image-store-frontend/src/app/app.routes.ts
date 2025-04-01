import { Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { CreateUserComponent } from './component/create-user/create-user.component';
import { ListImagesComponent } from './component/list-images/list-images.component';
import { ImageComponent } from './component/image/image.component';
import { UploadComponent } from './component/upload/upload.component';

export const routes: Routes = [
    {
        path: '',
        component: LoginComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'create-user',
        component: CreateUserComponent
    },
    {
        path: 'list-images',
        component: ListImagesComponent
    },
    {
        path: 'image/:fileName',
        component: ImageComponent
    },
    {
        path: 'upload',
        component: UploadComponent
    }
];
