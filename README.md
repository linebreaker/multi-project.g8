This is a [giter8](https://github.com/n8han/giter8) template for bootstrapping
a new multi-module SBT project with a default Novus configuration.

* SBT 0.13.1
* Cross build against Scala 2.9.3, 2.10.3
* Aggregate project with a single "core" sub project
* [Scala Test](http://www.scalatest.org/)
* Project `name`, `organization` and `version` customizable as variables (Novus defaults)
* Novus code formatting standards (scalariform)
* SBT plugins: [sbt-dependency-graph](https://github.com/jrudolph/sbt-dependency-graph), [sbt-idea](https://github.com/mpeltonen/sbt-idea). [sbtscalariform](https://github.com/typesafehub/sbtscalariform)
* Everything in your project's base package imported automatically in repl sessions
