import os
import urllib.request
import re


year = 2025
url = f"https://adventofcode.com/{year}"

request = urllib.request.Request(
    url,
    headers={
        "Cookie": f"session={os.environ['AOC_SESSION']}"
    }
)

with urllib.request.urlopen(request) as response:
    html = response.read().decode("utf-8")

print(f"Downloaded {len(html)} bytes")

with urllib.request.urlopen(request) as response:
    html = response.read().decode("utf-8")

print(f"Downloaded {len(html)} bytes")
print(html[:500])

matches = re.findall(r'completion_day_level.*', html)

for match in matches[:5]:
    print(match)