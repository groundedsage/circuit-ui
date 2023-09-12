(ns app.todo-list
  #?(:cljs (:require-macros app.todo-list))
  (:require contrib.str
            [hyperfiddle.electric-ui4 :as ui4]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.electric-ui4 :as ui]))

#?(:cljs (defonce !page (atom nil)))
(e/def page (e/client (e/watch !page)))

(def ui-components
  ["Accordion" 
   "Alert"
   "Alert Dialog"
   "Aspect Ratio"
   "Avatar"
   "Badge"
   "Button"
   "Calendar"
   "Card"
   "Checkbox"
   "Collapsible"
   "Combobox"
   "Command"
   "Context Menu"
   "Data Table"
   "Date Picker"
   "Dialog"
   "Dropdown Menu"
   "Form"
   "Hover Card"
   "Input"
   "Label"
   "Menubar"
   "Navigation Menu"
   "Popover"
   "Progress"
   "Radio Group"
   "Scroll Area"
   "Select"
   "Seperator"
   "Sheet"
   "Skeleton"
   "Slider"
   "Switch"
   "Table"
   "Tabs"
   "Textarea"
   "Toast"
   "Toggle"
   "Tooltip"])

(e/defn AccordionItem [{:keys [trigger content]}]
  {:trigger (if (string? trigger)
              (dom/button 
                (dom/props {:class "border-b"})
                (dom/on "click" (e/fn [_] (println "Opening:" trigger)))
                (dom/text trigger))
              (trigger))
   :content (if (string? content)
              (dom/p (dom/text content))
              (content))})

(e/defn Collapsible [{:keys [trigger content disabled? default-open?]}]
  (let [!open? (atom default-open? true false)
        open? (e/watch !open?)]
    (println "this is the content: " trigger)
    (ui4/button (e/fn [] (reset! !open? (not @!open?)))
      (dom/props {:type "button"
                  :aria-expanded open?
                  :disabled disabled?
                  :data-state open?
                  :data-disabled disabled?})
      (dom/div
        (if (string? trigger) 
          (dom/text trigger)
          trigger)))
    (dom/div
      (dom/props {:hidden (not open?)})
      (if (string? content)
        (dom/p (dom/text content))
        content))))


(e/defn Page []
  (case page
    "Accordion"
    (dom/div
      (dom/h1 (dom/text "Accordion"))
      (dom/p (dom/text "A vertically stacked set of interactive headings that each reveal a section of content."))
      (dom/div (dom/props {:type "single"
                           :class "w-full"})
        (AccordionItem. {:trigger "Is it accessible?"
                         :content "Yes. It adheres to the WAI-ARIA design pattern."})))
    "Alert"
    (dom/div
      (dom/h1 (dom/text "Alert"))
      (dom/p (dom/text "Displays a callout for user attention.")))

    "Collapsible"
    (dom/div
      (dom/h1 (dom/text "Collapsible"))
      (dom/p (dom/text "An interactive component which expands/collapses a panel."))
      (dom/div (dom/props {:class  "flex items-center justify-between space-x-4 rounded-md p-4 border"})
        (dom/h4 
          (dom/props {:class "text-sm font-semibold"})
          (dom/text "@peduarte starred 3 repositories"))
        (Collapsible. {:trigger (dom/div
                                  (dom/img (dom/props {:src "./radix-icons/chevron-up.svg"}))
                                  (dom/img (dom/props {:src "./radix-icons/chevron-down.svg"})))
                       :content "Yes. Free to use for personal and commercial projects. No attribution
    required."})))
    (dom/div
      (dom/h1 (dom/text "Component not found"))
      (dom/p (dom/text "Component not found or not made yet")))))


(e/defn Todo-list []
  (e/client
    (dom/div (dom/props {:style {:display "flex"}})
      (dom/ul
        (e/for-by identity [c ui-components]
          (dom/li 
            (dom/on "click" (e/fn [_] (reset! !page c)))
            (dom/text c))))
      (Page.))))