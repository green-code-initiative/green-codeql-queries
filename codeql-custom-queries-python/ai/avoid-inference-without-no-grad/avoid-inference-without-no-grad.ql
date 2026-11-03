/**
 * @name Wrap PyTorch inference in torch.no_grad()
 * @description Using a PyTorch model in evaluation mode without wrapping inference in torch.no_grad() leads to unnecessary gradient tracking, increasing memory usage and energy consumption.
 * @kind problem
 * @problem.severity warning
 * @precision high
 * @id python/ai/avoid-inference-without-no-grad
 * @link https://green-code-initiative.org/rules#id:GCI100
 * @tags ai
 * @tags python
 */

import python

predicate isInsideNoGrad(Call call) {
  exists(With with_, Call noGradCall, Attribute attr |
    noGradCall.getFunc() = attr and
    attr.getName() = "no_grad" and
    attr.getObject().(Name).getId() = "torch" and
    with_.getContextExpr() = noGradCall and
    with_.getBody().contains(call)
  )
}

predicate isKnownModelMethod(string name) {
  name = "eval" or
  name = "train" or
  name = "forward" or
  name = "parameters" or
  name = "state_dict" or
  name = "load_state_dict" or
  name = "zero_grad" or
  name = "to" or
  name = "cuda" or
  name = "cpu"
}

predicate evalCalledOnNameInScope(string modelName, Function func) {
  exists(Call evalCall, Attribute attr |
    attr = evalCall.getFunc() and
    attr.getName() = "eval" and
    attr.getObject().(Name).getId() = modelName and
    evalCall.getScope() = func
  )
}

predicate trainCalledOnNameInScope(string modelName, Function func) {
  exists(Call trainCall, Attribute attr |
    attr = trainCall.getFunc() and
    attr.getName() = "train" and
    attr.getObject().(Name).getId() = modelName and
    trainCall.getScope() = func
  )
}

predicate isInferenceCallOnModel(Call inferenceCall, string modelName) {
  // Direct call: model(x)
  inferenceCall.getFunc().(Name).getId() = modelName
  or
  // Attribute call: self.model(x) — but NOT known PyTorch methods
  exists(Attribute attr |
    inferenceCall.getFunc() = attr and
    attr.getObject().(Name).getId() = modelName and
    not isKnownModelMethod(attr.getName())
  )
}

from Call inferenceCall, string modelName, Function func
where
  isInferenceCallOnModel(inferenceCall, modelName) and
  inferenceCall.getScope() = func and
  evalCalledOnNameInScope(modelName, func) and  // only flag if THIS variable has .eval()
  not trainCalledOnNameInScope(modelName, func) and
  not isInsideNoGrad(inferenceCall) and
  // Exclude calls that are arguments of another flagged inference call
  not exists(Call outerCall, string outerModel |
    isInferenceCallOnModel(outerCall, outerModel) and
    evalCalledOnNameInScope(outerModel, func) and
    not trainCalledOnNameInScope(outerModel, func) and
    outerCall.getAnArg() = inferenceCall
  )
select inferenceCall,
  "Wrap PyTorch inference in 'torch.no_grad()' to disable gradient tracking and reduce memory usage and energy consumption."