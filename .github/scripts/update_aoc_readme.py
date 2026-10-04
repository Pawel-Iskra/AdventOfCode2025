import os
import urllib.request

year = 2025
url = f"https://adventofcode.com/{year}"

request = urllib.request.Request(
    url,
    headers={
        "Cookie": f"session={os.environ['AOC2025_SESSION']}"
    }
)

with urllib.request.urlopen(request) as response:
    html = response.read().decode("utf-8")

print(f"Downloaded {len(html)} bytes")