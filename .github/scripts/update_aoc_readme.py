import os
import urllib.request

year = 2025
url = f"https://adventofcode.com/{year}/leaderboard/self"

request = urllib.request.Request(
    url,
    headers={
        "Cookie": f"session={os.environ['AOC_SESSION']}"
    }
)

with urllib.request.urlopen(request) as response:
    data = response.read().decode("utf-8")

print(f"Downloaded {len(data)} bytes")
print(data[:1000])