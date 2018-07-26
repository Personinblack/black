package me.blackness.black.scheme;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.blackness.black.Element;
import me.blackness.black.Scheme;

/*
       .                                                    .
    .$"                                    $o.      $o.  _o"
   .o$$o.    .o$o.    .o$o.    .o$o.   .o$$$$$  .o$$$$$ $$P  `4$$$$P'   .o$o.
  .$$| $$$  $$' $$$  $$' $$$  $$' $$$ $$$| $$$ $$$| $$$ ($o  $$$: $$$  $$' $$$
  """  """ """  """ """  """ """  """ """  """ """  """  "   """  """ """  """
.oOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo.
  ooo_ ooo ooo. ... ooo. ... ooo.  .. `4ooo.  .`4ooo.   ooo. ooo. ooo ooo.  ..
  $$$"$$$$ $$$| ... $$$| ... $$$$$$ ..    "$$o     "$$o $$$| $$$| $$$ $$$|   .
  $$$| $$$ $$$|     $$$|     $$$|     $$$: $$$ $$$: $$$ $$$| $$$| $$$ $$$|
  $$$| $$$ $$$| $o. $$$| $o. $$$| $o. $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $o.
  $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$ $$$| $$$
  $$$| $$$  $$. $$$  $$. $$$  $$. $$$ $$$| $$$ $$$| $$$ $$$| $$$| $$$  $$. $$$
  $$$: $P'  `4$$$Ü'__`4$$$Ü'  `4$$$Ü' $$$$$P'  $$$$$P'  $$$| $$$: $P' __`4$$$Ü'
 _ _______/∖______/  ∖______/∖______________/|________ "$P' _______/  ∖_____ _
                                                        i"  personinblack
                                                        |
 */
public final class BasicScheme implements Scheme {
    private final Character[][] layout;
    private final Map<Character, Element> mappings;

    public BasicScheme(final Character[][] layout, final Map<Character, Element> mappings) {
        this.layout = layout;
        this.mappings = mappings;
    }

    public BasicScheme(final Character[][] layout) {
        this(layout, new HashMap<>());
    }

    @Override
    public void map(final char character, final Element element) throws IllegalArgumentException {
        for (final Character[] row : layout) {
            if (!Arrays.stream(row).anyMatch(i -> i.charValue() == character)) {
                throw new IllegalArgumentException(
                    String.format("the character given %s does not exist in the layout", character)
                );
            }
        }
        mappings.put(character, element);
    }

    @Override
    public void unmap(char character) {
        mappings.remove(character);
    }

    @Override
    public void unmap(Element element) {
        for (final Map.Entry<Character, Element> entry : mappings.entrySet()) {
            if (entry.getValue().is(element)) {
                mappings.remove(entry.getKey());
            }
        }
    }
}
