// ============================================================
// 🚫 Noncompliant - Namespace imports from external libraries
// ============================================================


import * as lodash from "lodash"; // $ Alert
import * as underscore from "underscore"; // $ Alert
import * as R from "ramda"; // $ Alert
import * as dateFns from "date-fns"; // $ Alert
import * as moment from "moment"; // $ Alert
import * as axios from "axios"; // $ Alert
import * as $ from "jquery"; // $ Alert

// ============================================================
// 🚫 Noncompliant - Default imports from external libraries
// ============================================================

import lodash from "lodash"; // $ Alert
import _ from "underscore"; // $ Alert
import express from "express"; // $ Alert
import react from "react"; // $ Alert

// ============================================================
// ✅ Compliant - Import specific submodules
// ============================================================

import isEmpty from "lodash/isEmpty"; // OK
import map from "underscore/modules/map.js"; // OK
import format from "date-fns/format"; // OK
import isDate from "date-fns/isDate"; // OK

// ============================================================
// ✅ Compliant - Named imports (specific destructuring)
// ============================================================

import { isEmpty as empty } from "lodash"; // OK (specific named imports)
import { Router } from "express"; // OK (specific named imports)
import { useState } from "react"; // OK (specific named imports)

// ============================================================
// 🚫 Noncompliant - relative imports (not flagged)
// ============================================================

import * as utils from "./utils"; // KO // $ Alert
import * as helpers from "../helpers/index"; // // $ Alert

// ============================================================
// ✅ Compliant - Local imports (not flagged)
// ============================================================
import { config } from "./config"; // OK (local module)

// ============================================================
// ✅ Compliant - Scoped packages with specific imports
// ============================================================

import { parseISO } from "@date-fns/parse"; // OK (specific named import)
import { cloneDeep } from "@lodash-es/clone-deep"; // OK (specific named import)
