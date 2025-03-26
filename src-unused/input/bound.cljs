(ns pinkgorilla.input.bound
  (:require
   ; helper fns
   [pinkgorilla.input.bind :refer [bind]]
   [pinkgorilla.input.checkbox :refer [checkbox]]
   [pinkgorilla.input.text :refer [textbox]]
   [pinkgorilla.input.select :refer [select-nav]]))

(def text-a (bind textbox))
(def checkbox-a (bind checkbox))
(def select-a (bind select-nav))




