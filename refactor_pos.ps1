Move-Item 'e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\models\entities\Order.java' 'e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\pos\entities\'
Move-Item 'e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\models\entities\OrderItem.java' 'e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\pos\entities\'

$f1='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\pos\controllers\POSController.java'
$c1=Get-Content $f1 -Raw
$c1=$c1 -replace 'package com\.aulkhami\.pakupos\.modules\.pos;', "package com.aulkhami.pakupos.modules.pos.controllers;`nimport com.aulkhami.pakupos.modules.pos.models.POSModel;`nimport com.aulkhami.pakupos.modules.pos.interactors.POSInteractor;"
Set-Content $f1 $c1

$f2='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\pos\models\POSModel.java'
$c2=Get-Content $f2 -Raw
$c2=$c2 -replace 'package com\.aulkhami\.pakupos\.modules\.pos;', 'package com.aulkhami.pakupos.modules.pos.models;'
Set-Content $f2 $c2

$f3='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\pos\view\POSView.java'
$c3=Get-Content $f3 -Raw
$c3=$c3 -replace 'package com\.aulkhami\.pakupos\.modules\.pos;', "package com.aulkhami.pakupos.modules.pos.view;`nimport com.aulkhami.pakupos.modules.pos.models.POSModel;`nimport com.aulkhami.pakupos.modules.pos.interactors.POSInteractor;"
Set-Content $f3 $c3

$f4='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\pos\interactors\POSInteractor.java'
$c4=Get-Content $f4 -Raw
$c4=$c4 -replace 'package com\.aulkhami\.pakupos\.modules\.pos;', "package com.aulkhami.pakupos.modules.pos.interactors;`nimport com.aulkhami.pakupos.modules.pos.models.POSModel;"
Set-Content $f4 $c4

$f5='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\pos\entities\Order.java'
$c5=Get-Content $f5 -Raw
$c5=$c5 -replace 'package com\.aulkhami\.pakupos\.models\.entities;', 'package com.aulkhami.pakupos.modules.pos.entities;'
Set-Content $f5 $c5

$f6='e:\coding\project\pakupos\src\main\java\com\aulkhami\pakupos\modules\pos\entities\OrderItem.java'
$c6=Get-Content $f6 -Raw
$c6=$c6 -replace 'package com\.aulkhami\.pakupos\.models\.entities;', 'package com.aulkhami.pakupos.modules.pos.entities;'
Set-Content $f6 $c6
