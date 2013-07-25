package $organization$.$name;format="lower,word"$

import org.specs2.mutable._

class AppSpec extends WordSpec with ShouldMatchers {

  "The 'Hello world' string" should {
    "contain 11 characters" in {
      "Hello world" should have length (11)
    }
    "start with 'Hello'" in {
      "Hello world" should startWith ("Hello")
    }
    "end with 'world'" in {
      "Hello world" should endWith("world")
    }
  }
}
