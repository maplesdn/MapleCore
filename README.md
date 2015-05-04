# MapleCore

This project is an implementation (still in early stage) of the Maple programming model entirely in Java. It provides the Maple programming model as well as a simple tracing runtime and trace tree compiler. This project does not provide a network controller. Instead, it is implemented in terms of a simple southbound API that must be implemented by a network controller. This allows MapleCore to be controller-agnostic and to be applied to multiple controller platforms. The maplesdn/odlmaple project will provide an implementation of the MapleCore southbound API for OpenDaylight.
