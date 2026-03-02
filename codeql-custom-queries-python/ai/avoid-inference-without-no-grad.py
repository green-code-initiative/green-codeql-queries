import torch
import torch.nn as nn

# ==============================================================================
# COMPLIANT CASES
# ==============================================================================

# ✅ Compliant - simple inference wrapped in torch.no_grad()
def compliant_simple():
    model = nn.Linear(10, 1)
    model.eval()
    with torch.no_grad():
        return model(torch.randn(1, 10))  # OK

# ✅ Compliant - inference loop wrapped in torch.no_grad()
def compliant_loop(dataloader):
    model = nn.Linear(10, 1)
    model.eval()
    with torch.no_grad():
        for inputs in dataloader:
            outputs = model(inputs)  # OK

# ✅ Compliant - multiple models wrapped in torch.no_grad()
def compliant_multi_model():
    model = nn.Linear(10, 1)
    encoder = nn.Linear(512, 512)
    model.eval()
    encoder.eval()
    with torch.no_grad():
        features = encoder(torch.randn(1, 512))  # OK
        return model(features)  # OK

# ✅ Compliant - validation loop inside torch.no_grad()
def compliant_validation(val_loader, criterion):
    model = nn.Linear(10, 1)
    model.eval()
    with torch.no_grad():
        val_loss = 0
        for inputs, labels in val_loader:
            outputs = model(inputs)  # OK
            val_loss += criterion(outputs, labels).item()

# ✅ Compliant - training mode, gradient tracking is expected
def compliant_training(dataloader, criterion, optimizer):
    model = nn.Linear(10, 1)
    model.train()
    for batch, labels in dataloader:
        output = model(batch)  # OK
        loss = criterion(output, labels)
        loss.backward()
        optimizer.step()

# ✅ Compliant - fine-tuning, train() explicitly called on multiple models
def compliant_finetuning():
    encoder = nn.Linear(512, 512)
    classifier = nn.Linear(512, 10)
    encoder.train()
    classifier.train()
    return classifier(encoder(torch.randn(1, 512)))  # OK

# ==============================================================================
# NONCOMPLIANT CASES
# ==============================================================================

# 🚫 Noncompliant - eval() called but no torch.no_grad()
def noncompliant_simple():
    model = nn.Linear(10, 1)
    model.eval()
    return model(torch.randn(1, 10))  # $ Alert

# 🚫 Noncompliant - eval loop without torch.no_grad()
def noncompliant_loop(dataloader):
    model = nn.Linear(10, 1)
    model.eval()
    for inputs in dataloader:
        outputs = model(inputs)  # $ Alert

# 🚫 Noncompliant - multiple models in eval mode, no torch.no_grad()
def noncompliant_multi_model():
    model = nn.Linear(10, 1)
    encoder = nn.Linear(512, 512)
    model.eval()
    encoder.eval()
    features = encoder(torch.randn(1, 512))  # $ Alert
    return model(features)  # $ Alert

# 🚫 Noncompliant - validation loop without torch.no_grad()
def noncompliant_validation(val_loader, criterion):
    model = nn.Linear(10, 1)
    model.eval()
    val_loss = 0
    for inputs, labels in val_loader:
        outputs = model(inputs)  # $ Alert
        val_loss += criterion(outputs, labels).item()

# 🚫 Noncompliant - nested call without torch.no_grad()
def noncompliant_nested():
    encoder = nn.Linear(512, 512)
    classifier = nn.Linear(512, 10)
    encoder.eval()
    classifier.eval()
    return classifier(encoder(torch.randn(1, 512)))  # $ Alert

# 🚫 Noncompliant - benchmark without torch.no_grad()
def noncompliant_benchmark():
    model = nn.Linear(10, 1)
    model.eval()
    for _ in range(100):
        dummy_input = torch.randn(1, 10)
        output = model(dummy_input)  # $ Alert