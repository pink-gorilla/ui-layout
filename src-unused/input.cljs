(ns input
  (:require
   [pinkgorilla.input.button]
   [pinkgorilla.input.progressbar]
   [pinkgorilla.input.bound]))

(def button pinkgorilla.input.button/button)

(def progressbar pinkgorilla.input.progressbar/progressbar)

;input bound
(def textbox pinkgorilla.input.bound/text-a)

(def select pinkgorilla.input.bound/select-a)

(def checkbox pinkgorilla.input.bound/checkbox-a)
