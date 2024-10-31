package blui.ui;

import arc.func.*;
import arc.graphics.*;
import arc.scene.*;
import arc.scene.style.*;
import arc.scene.ui.*;
import arc.scene.ui.ImageButton.*;
import arc.scene.ui.TextField.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import blui.*;
import mindustry.gen.*;

public class BLElements{
    public static Element[] sliderSet(Table t, Cons<String> changed, Prov<String> fieldText, TextFieldFilter filter, TextFieldValidator valid, float min, float max, float step, float def, Cons2<Float, TextField> sliderChanged, String title, String tooltip){
        TextField field = textField(String.valueOf(def), changed, fieldText, filter, valid);

        Label tab = t.add(title).right().padRight(6f).get();
        Slider sl = t.slider(min, max, step, def, s -> sliderChanged.get(s, field)).right().width(BLVars.sliderWidth).get();
        TextField f = t.add(field).left().padLeft(6f).width(BLVars.fieldWidth).get();

        if(tooltip != null){
            Tooltip tip = new Tooltip(to -> to.background(Tex.button).add(tooltip)){{
                allowMobile = true;
            }};
            tab.addListener(tip);
            sl.addListener(tip);
            f.addListener(tip);
        }

        return new Element[]{sl, f};
    }

    public static TextField textField(String text, Cons<String> changed, Prov<String> setText, TextFieldFilter filter, TextFieldValidator valid){
        TextField field = new TextField(text);
        if(filter != null) field.setFilter(filter);
        if(valid != null) field.setValidator(valid);
        field.changed(() -> {
            if(field.isValid()) changed.get(field.getText());
        });
        if(setText != null){
            field.update(() -> {
                Scene stage = field.getScene();
                if(!(stage != null && stage.getKeyboardFocus() == field))
                    field.setText(setText.get());
            });
        }

        return field;
    }

    public static Cell<ImageButton> imageButton(Table t, Drawable icon, ImageButtonStyle style, float isize, Runnable listener, Prov<CharSequence> label, String tooltip){
        Cell<ImageButton> bCell = t.button(icon, style, isize, listener);
        ImageButton b = bCell.get();
        if(label != null){
            Cell<Label> lab = b.label(label).padLeft(6f).expandX().name("label");
            lab.update(l -> {
                l.setText(label.get());
                l.setColor(b.isDisabled() ? Color.lightGray : Color.white);
            });
        }
        if(tooltip != null) boxTooltip(b, tooltip);

        return bCell;
    }

    public static Stack itemImage(TextureRegionDrawable region, Prov<CharSequence> text){
        return itemImage(region, text, Color.white, Color.white, 1f, Align.bottomLeft);
    }

    public static Stack itemImage(TextureRegionDrawable region, Prov<CharSequence> text, Color icolor, Color tcolor, float fontScl, int align){
        Stack stack = new Stack();

        Table t = new Table().align(align);
        t.label(text).color(tcolor).fontScale(fontScl);

        Image i = new Image(region).setScaling(Scaling.fit);
        i.setColor(icolor);

        stack.add(i);
        stack.add(t);
        return stack;
    }

    public static void divider(Table t, String label, Color color, int colSpan){
        if(label != null){
            t.add(label).growX().color(color).colspan(colSpan).left();
            t.row();
        }
        t.image().growX().pad(5f, 0f, 5f, 0f)
            .height(3f).color(color).colspan(colSpan).left();
        t.row();
    }

    public static void divider(Table t, String label, Color color){
        divider(t, label, color, 1);
    }

    public static void boxTooltip(Element e, Prov<CharSequence> text){
        e.addListener(new Tooltip(t -> t.background(Tex.button).label(text)){{
            allowMobile = true;
        }});
    }

    public static void boxTooltip(Element e, String text){
        e.addListener(new Tooltip(t -> t.background(Tex.button).add(text)){{
            allowMobile = true;
        }});
    }
}
