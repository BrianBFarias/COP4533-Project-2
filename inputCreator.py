import random

size=5000

# Parameters for the first line
param1 = size
param2 = 40

# Generate 1000 random integers between 1 and 100 for each of the following lines
n = size
line2 = " ".join(str(random.randint(1, 20)) for _ in range(n))
line3 = " ".join(str(random.randint(1, 20)) for _ in range(n))

# Format the output as a mock input
mock_input = f"{param1} {param2}\n{line2}\n{line3}"

# Save the output to a text file
with open("5000_input.txt", "w") as file:
    file.write(mock_input)

print("Mock input saved to mock_input.txt")
