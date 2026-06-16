$f1='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\auth\controllers\LoginController.java'
$c1=Get-Content $f1 -Raw
$c1=$c1 -replace 'package com\.aulkhami\.pakupos\.modules\.auth;', "package com.aulkhami.pakupos.modules.auth.controllers;`nimport com.aulkhami.pakupos.app.controllers.Controller;`nimport com.aulkhami.pakupos.modules.auth.models.LoginModel;`nimport com.aulkhami.pakupos.modules.auth.interactors.LoginInteractor;"
Set-Content $f1 $c1

$f2='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\auth\models\LoginModel.java'
$c2=Get-Content $f2 -Raw
$c2=$c2 -replace 'package com\.aulkhami\.pakupos\.modules\.auth;', 'package com.aulkhami.pakupos.modules.auth.models;'
Set-Content $f2 $c2

$f3='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\auth\view\LoginView.java'
$c3=Get-Content $f3 -Raw
$c3=$c3 -replace 'package com\.aulkhami\.pakupos\.modules\.auth;', "package com.aulkhami.pakupos.modules.auth.view;`nimport com.aulkhami.pakupos.modules.auth.models.LoginModel;`nimport com.aulkhami.pakupos.modules.auth.interactors.LoginInteractor;"
Set-Content $f3 $c3

$f4='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\auth\interactors\LoginInteractor.java'
$c4=Get-Content $f4 -Raw
$c4=$c4 -replace 'package com\.aulkhami\.pakupos\.modules\.auth;', "package com.aulkhami.pakupos.modules.auth.interactors;`nimport com.aulkhami.pakupos.modules.auth.models.LoginModel;"
Set-Content $f4 $c4
