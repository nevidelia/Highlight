# Highlight
*Syntax Highlighter for Android Apps*

**Supported languages**
* C
* Java
* PHP

**Add it to your root build.gradle**
```
    allprojects {
        repositories {
            ...
            maven {
                url 'https://jitpack.io'
            }
        }
    }
```

**Add the dependency to app build.gradle**
```
    dependencies {
        implementation 'com.github.nevidelia:Highlight:1.0.0'
    }
```

**How it works?**
```
    Highlight highlight = new Highlight();
```

**Customize colors**
```
    // set colors for targets

    // pass resource colors
    highlight.setColors(this, R.color.variables, R.color.statements, R.color.numbers, R.color.strings, R.color.comments, R.color.functions, R.color.black);

    // or pass int colors
    highlight.setColors(Color.MAGENTA, Color.RED, Color.BLUE, Color.GREEN, Color.GRAY, Color.YELLOW, Color.BLACK);

    // or pass hex colors
    highlight.setColors("#660e7a", "#ff2022", "#2022a5", "#20223c", "#202277", "#FF2022", "#202200");
```

**Highlight code**
```
    // highlight a piece of code

    // pass language and code
    textView.setText(highlight.code(C,"#include<stdio.h>\nvoid main()\n{\n\b\b\b\bprintf(\"Hello\bworld\");\n}"));
```

**License**
```
    Copyright 2022 Nevidelia Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
