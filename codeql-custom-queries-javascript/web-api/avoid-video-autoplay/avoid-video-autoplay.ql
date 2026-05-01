/**
 * @name Avoid automatic video or audio preloading
 * @description Autoplaying or preloading media (video/audio) consumes unnecessary energy and bandwidth, increasing environmental impact and data usage.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @link https://green-code-initiative.org/rules#id:GCI36
 * @id js/web-api/avoid-media-autoplay
 * @tags web-api
 * @tags server
 * @tags js
 */

import javascript

from JsxElement media, JsxAttribute attr
where
  (media.getName() = "video" or media.getName() = "audio") and
  attr = media.getAnAttribute() and
  (
    attr.getName() = "autoPlay"
    or
    (
      attr.getName() = "preload" and
      attr.getValue().(StringLiteral).getValue().regexpMatch("(?i)auto|metadata")
    )
  )
select media, "Avoid 'autoPlay' or 'preload=\"auto/metadata\"' on " + media.getName() + " elements. Use 'preload=\"none\"' to reduce energy consumption and unnecessary data usage."