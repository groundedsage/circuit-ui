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

(e/defn TypographyH1 [s]
  (dom/h1 
    (dom/props {:class "scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl"})
    (dom/text s)))

(e/defn TypographyH2 [s]
  (dom/h1
    (dom/props {:class "scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight transition-colors first:mt-0"})
    (dom/text s)))

(e/defn TypographyH3 [s]
  (dom/h1
    (dom/props {:class "scroll-m-20 text-2xl font-semibold tracking-tight"})
    (dom/text s)))

(e/defn TypographyH4 [s]
  (dom/h1
    (dom/props {:class "scroll-m-20 text-xl font-semibold tracking-tight"})
    (dom/text s)))


(e/defn TypographyP [s]
  (dom/p 
    (dom/props {:class "leading-7 [&:not(:first-child)]:mt-6"})
    (dom/text s)))

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

(e/defn Alert [{:keys [title desc]}]
  (dom/div (dom/props {:role "alert"})
    (dom/img (dom/props {:src "./radix-icons/rocket.svg"
                         :class "h-4 w-4"}))
    (dom/h5 
      (dom/props {:class "mb-1 font-medium leading-none tracking-tight"})
      (dom/text title))
    (dom/p 
      (dom/props {:class "text-sm [&_p]:leading-relaxed"})
      (dom/text desc))))

(e/defn Collapsible [{:keys [trigger content disabled? default-open?]}]
  (let [!open? (atom (if default-open? true false))
        open? (e/watch !open?)]
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

(e/defn Card [{:keys [title desc footer content]}]
  (dom/div 
    (dom/props {:class "rounded-xl border bg-card text-card-foreground shadow"})
    (dom/div (dom/props {:class "flex flex-col space-y-1.5 p-6"})
      (dom/div
        (dom/props {:class "font-semibold leading-none tracking-tight"})
        (dom/text title))
      (dom/div
        (dom/props {:class "text-sm text-muted-foreground"})
        (dom/text desc))
      (dom/div 
        (dom/props {:class "p-6 pt-0"})
        (dom/text content))
      (dom/div 
        (dom/props {:class "flex items-center p-6 pt-0"})
        (dom/text footer)))))

(def wrapper {:class "flex items-center justify-between space-x-4 rounded-md p-4 border"})


(e/defn Page []
  (case page
    "Accordion"
    (dom/div
      (TypographyH1. "Accordion")
      (dom/p (dom/text "A vertically stacked set of interactive headings that each reveal a section of content."))
      (dom/div (dom/props wrapper)
        (dom/div (dom/props {:type "single"
                             :class "w-full"})
          (AccordionItem. {:trigger "Is it accessible?"
                           :content "Yes. It adheres to the WAI-ARIA design pattern."}))))
    "Alert"
    (dom/div
      (TypographyH1. "Alert")
      (dom/p (dom/text "Displays a callout for user attention."))
      (dom/div (dom/props wrapper)
        (Alert. {:title "Heads up!"
                 :desc "You can add components to your app using the cli."})))

    "Typography"
    (dom/div
      (TypographyH1. "Typography")
      (dom/p (dom/text "Styles for headings, paragraphs, lists...etc"))
      (dom/div (dom/props wrapper)
        (dom/div
          (TypographyH1. "The Joke Tax Chronicles")
          (TypographyP. "Once upon a time, in a far-off land, there was a very lazy king who spent all day lounging on his throne. One day, his advisors came to him with a problem: the kingdom was running out of money."))))

    "Collapsible"
    (dom/div
      (TypographyH1. "Collapsible")
      (dom/p (dom/text "An interactive component which expands/collapses a panel."))
      (dom/div (dom/props wrapper)
        ;; Component
        (dom/h4
          (dom/props {:class "text-sm font-semibold"})
          (dom/text "@peduarte starred 3 repositories"))
        (let [trigger (dom/div
                        (dom/img (dom/props {:src "./radix-icons/chevron-up.svg"}))
                        (dom/img (dom/props {:src "./radix-icons/chevron-down.svg"})))]
          (println trigger)
          (Collapsible. {:trigger trigger
                         :content "Yes. Free to use for personal and commercial projects. No attribution
    required."}))))

    "Card"
    (dom/div
      (TypographyH1. "Card")
      (dom/p (dom/text "Displays a card with header, content, and footer."))
      (dom/div (dom/props wrapper)
            (Card. {:title "Create project"
                    :desc "Deploy your new project in one-click"
                    :content "Hello world"
                    :footer "Cancel"})
        
        ))
    (dom/div
      (dom/h1 (dom/text "Component not found"))
      (dom/p (dom/text "Component not found or not made yet")))))


(e/defn Todo-list []
  (e/client
    (dom/div (dom/props {:style {:display "flex"}})
      (dom/div (dom/props {:class "flex flex-col p-4"})
        (dom/button 
          (dom/on "click" (e/fn [_] (reset! !page "Typography")))
          (dom/text "Typography"))
        (dom/p
          (dom/props {:class "font-bold"})
          (dom/text "Components"))
        (dom/ul
          (e/for-by identity [c ui-components]
            (dom/li 
              (dom/on "click" (e/fn [_] (reset! !page c)))
              (dom/text c)))))
      (Page.))))