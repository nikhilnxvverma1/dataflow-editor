package model.component.library;

import model.component.Input;
import model.component.Name;
import model.component.Output;

@Name(value = "String Multiply",description = "Multiplies the string that many times")
public class MyComponent {

    @Input("multiple")
    Integer m;
    @Input()
    String text;

    @Output("multipliedString")
    String concatenated;
}
